package helpers

import org.grails.datastore.mapping.query.api.Criteria
import org.springframework.stereotype.Component

@Component
/**
 * This class creates a closure to perform a filter using a createCriteria based on queryString parameters
 * 
 * It's based on https://github.com/Grails-Plugin-Consortium/grails-filterpane/blob/master/grails-app/services/org/grails/plugin/filterpane/FilterPaneService.groovy
 * 
 * TODO: Unit test, handle wrong field path
 *
 */
class RestSearchHelper {

	private static String EQUALS_CHAR = '!';

	public Closure queryFromQueryString(Criteria criteria, Class clazz, Map queryString, Integer offset, Integer max, String sortBy, String order) {
		return this.buildCriteriaFromQueryString(criteria, clazz, queryString, offset, max, sortBy, order, false );
	}

	public Closure countFromQueryString(Criteria criteria, Class clazz, Map queryString) {
		return this.buildCriteriaFromQueryString(criteria, clazz, queryString, null, null, null, null, true);
	}

	private Closure buildCriteriaFromQueryString(Criteria criteria, Class clazz, Map queryString, Integer offset, Integer max, String sortBy,  String orderBy, boolean doCount ) {

		def parsed = []
		def aliases = [:]

		queryString.each { fieldPath, fieldValue ->
			def parsedField = [:]

			if(fieldPath.contains(".")) {
				aliases << parseAliases(fieldPath)
				parsedField = parseFieldPath(fieldPath)
			} else {
				parsedField.fieldPath = fieldPath;
			}
			
			parsedField.fullFieldPath = fieldPath
			parsedField.fieldValue = fieldValue;

			parsed.add(parsedField);
		}

		//Convert sorting to aliases
		def sortingOrders = [];
		def sortingFields =  [];
		if(orderBy) {
			sortingOrders = orderBy.tokenize("|")
		}

		if(sortBy) {
			def index = 0
			sortingFields = sortBy.tokenize("|").collect{
				def sorting = [:]
				if(it.contains(".")) {
					aliases << parseAliases(it)
					def parsedField = parseFieldPath(it);
					sorting.field =  parsedField.alias + "." + parsedField.field;
				} else {
					sorting.field = it;
				}
				sorting.order = sortingOrders.get(index)
				index++;
				return sorting;
			}
		}


		def criteriaClosure = {

			aliases.collect { alias, path ->
				return createAlias(path, alias)
			}

			and {
				parsed.collect { aParsedField ->

					def castedFieldValue = castField(clazz, aParsedField.fullFieldPath, aParsedField.fieldValue)
					if(aParsedField?.alias) {
						processField(criteria, "${aParsedField.alias}.${aParsedField.field}", castedFieldValue)
					} else {
						processField(criteria, aParsedField.fieldPath, castedFieldValue)
					}
				}
			}

			sortingFields.collect { sorting ->
				return criteria.order(sorting.field, sorting.order)
			}

			if(doCount) {
				criteria.projections { rowCount() }
			} else {
				firstResult(offset)
				maxResults(max)
			}
		}

		return criteriaClosure
	}
	
	private parseAliases(String toParse) {
		def alias = [:]

		def splitted = toParse.tokenize(".");
		// ie: f1.f2.f3.f4 -> [ (f1, f1), (f1f2, f1.f2), (f1f2f3, f1f2.f3)]
		// Last item isn't part of the path navigation, its the field so we discard it
		splitted.pop();		
		def end = 0
		splitted.each {
			def anAlias = splitted[0 .. end].join("");
			alias[anAlias] =  splitted[0 .. end].join(".");
			end++;	
		}
		
		return alias;		
	}

	private parseFieldPath(String toParse) {
		def path = toParse.tokenize(".");
		def parsedField = [:]
		parsedField.field = path.pop();
		parsedField.fieldPath = path.join(".");
		parsedField.alias = path.join("");
		return parsedField
	}
	
	private Class findField(Class clazz, String fieldName) {

		Class currentClazz = clazz;
		Map properties = [:]

		fieldName.tokenize('.').each {
			properties = currentClazz.getGormPersistentEntity().getPersistentProperties().groupBy { it.name };
			currentClazz = properties[it].type[0]; //because group by is used
		}
		if(clazz.equals(currentClazz))
			return null;
		return currentClazz;
	}

	private Criteria processType(criteria, Class clazz, String fieldKey, String fieldValue) {
		Class fieldType = findField(clazz, fieldKey);

		if(fieldType.equals(String.class)) {
			return processString(criteria, fieldType, fieldKey, fieldValue);
		} else {
			def parsedValue = fieldValue.asType(fieldType);
			return criteria.eq(fieldKey, parsedValue);
		}
	}

	private Criteria processString(Criteria criteria, Class fieldType, String fieldKey, String fieldValue) {
		if(fieldValue[0] == EQUALS_CHAR) {
			return criteria.eq(fieldKey,  fieldValue.substring(1));
		} else {
			return criteria.ilike(fieldKey, "%" + fieldValue + "%");
		}
	}

	private castField(Class clazz, String fullFieldPath, String fieldValue) {

		Class currentClazz = clazz;
		Map properties = [:]

		def splitted = fullFieldPath.tokenize('.');
		def toReturn = null;

		splitted.each { it ->

			if(it.equals("id")) {
				toReturn = fieldValue.asType(Long.class);
			} else {
				properties = currentClazz.getGormPersistentEntity().getPersistentProperties().groupBy { it.name };
				currentClazz = properties[it].type[0]; //because group by is used

				if(it == splitted.last()) {
					toReturn = fieldValue.asType(currentClazz);
				}
			}


		}
		return toReturn;
	}

	private Criteria processField(Criteria criteria, String path, fieldValue) {
		if(fieldValue.getClass().equals(String.class)) {
			if(fieldValue?.trim()){
				if(fieldValue[0] == EQUALS_CHAR) {
					return criteria.eq(path,  fieldValue.substring(1));
				} else {
					return criteria.ilike(path, "%" + fieldValue + "%");
				}
			}
		} else {
			return criteria.eq(path, fieldValue);
		}
	}
}
