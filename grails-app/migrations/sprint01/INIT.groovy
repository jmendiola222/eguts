package sprint01;

databaseChangeLog = {

	changeSet(author: "julian (generated)", id: "1436111785471-1") {
		createTable(tableName: "end_point") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "end_pointPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "descriptor", type: "varchar(255)")

			column(name: "email_template", type: "varchar(255)")

			column(name: "is_public", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-2") {
		createTable(tableName: "end_point_element") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "end_point_elePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "end_point_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "type", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-3") {
		createTable(tableName: "end_pointhtmlstgy") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "end_pointhtmlPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-4") {
		createTable(tableName: "end_pointjsonstgy") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "end_pointjsonPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-5") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "authority", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-6") {
		createTable(tableName: "subscription") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "subscriptionPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "end_date", type: "datetime")

			column(name: "end_point_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "start_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "subscriber_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "url", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-7") {
		createTable(tableName: "subscription_result") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "subscription_PK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "item_list_hash", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "last_end", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_start", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_sucessful_update", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "status", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "subscription_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-8") {
		createTable(tableName: "user") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "account_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "account_locked", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "enabled", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "last_password_change", type: "datetime")

			column(name: "log_in_attemps", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)")

			column(name: "password_expired", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_profile_id", type: "bigint")

			column(name: "username", type: "varchar(50)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-9") {
		createTable(tableName: "user_profile") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_profilePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-10") {
		createTable(tableName: "user_role") {
			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "user_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-11") {
		addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
	}

	changeSet(author: "julian (generated)", id: "1436111785471-20") {
		createIndex(indexName: "FK_k6jn0834ubpjru7892cswltkj", tableName: "end_point_element") {
			column(name: "end_point_id")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-21") {
		createIndex(indexName: "authority_uniq_1436111785393", tableName: "role", unique: "true") {
			column(name: "authority")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-22") {
		createIndex(indexName: "FK_3kkresacl7x0iowqykpvlc7vp", tableName: "subscription") {
			column(name: "end_point_id")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-23") {
		createIndex(indexName: "FK_r474sokipshlwph07uvsukmja", tableName: "subscription") {
			column(name: "subscriber_id")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-24") {
		createIndex(indexName: "FK_14iqmse7cgeqjkwc2swphkw77", tableName: "subscription_result") {
			column(name: "subscription_id")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-25") {
		createIndex(indexName: "FK_2ek1mbe9ojg3q7p83vusnrj15", tableName: "user") {
			column(name: "user_profile_id")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-26") {
		createIndex(indexName: "FK_qleu8ddawkdltal07p8e6hgva", tableName: "user") {
			column(name: "role_id")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-27") {
		createIndex(indexName: "email_uniq_1436111785401", tableName: "user", unique: "true") {
			column(name: "email")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-28") {
		createIndex(indexName: "FK_apcc8lxk2xnug8377fatvbn04", tableName: "user_role") {
			column(name: "user_id")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-29") {
		createIndex(indexName: "FK_it77eq964jhfqtu54081ebtio", tableName: "user_role") {
			column(name: "role_id")
		}
	}

	changeSet(author: "julian (generated)", id: "1436111785471-12") {
		addForeignKeyConstraint(baseColumnNames: "end_point_id", baseTableName: "end_point_element", constraintName: "FK_k6jn0834ubpjru7892cswltkj", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "end_point", referencesUniqueColumn: "false")
	}

	changeSet(author: "julian (generated)", id: "1436111785471-13") {
		addForeignKeyConstraint(baseColumnNames: "end_point_id", baseTableName: "subscription", constraintName: "FK_3kkresacl7x0iowqykpvlc7vp", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "end_point", referencesUniqueColumn: "false")
	}

	changeSet(author: "julian (generated)", id: "1436111785471-14") {
		addForeignKeyConstraint(baseColumnNames: "subscriber_id", baseTableName: "subscription", constraintName: "FK_r474sokipshlwph07uvsukmja", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "julian (generated)", id: "1436111785471-15") {
		addForeignKeyConstraint(baseColumnNames: "subscription_id", baseTableName: "subscription_result", constraintName: "FK_14iqmse7cgeqjkwc2swphkw77", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "subscription", referencesUniqueColumn: "false")
	}

	changeSet(author: "julian (generated)", id: "1436111785471-16") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user", constraintName: "FK_qleu8ddawkdltal07p8e6hgva", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "julian (generated)", id: "1436111785471-17") {
		addForeignKeyConstraint(baseColumnNames: "user_profile_id", baseTableName: "user", constraintName: "FK_2ek1mbe9ojg3q7p83vusnrj15", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user_profile", referencesUniqueColumn: "false")
	}

	changeSet(author: "julian (generated)", id: "1436111785471-18") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK_it77eq964jhfqtu54081ebtio", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "julian (generated)", id: "1436111785471-19") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK_apcc8lxk2xnug8377fatvbn04", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}
}
