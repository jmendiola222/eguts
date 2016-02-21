<div>
    <div class="alert alert-warning fade in" ng-show="items.length == 0">
        <i class="fa fa-fw fa-lg fa-exclamation"></i> <strong>{{noItemsMessage}}</strong>
    </div>

    <div ng-show="items.length > 0">
        <div class="dataTables_wrapper form-inline">
            <div class="table-wrapper">
                <table
                        ng-class="tableClass">
                    <thead>
                    <tr>
                        <th ng-repeat="column in columns">{{column.name}}</th>
                        <th ng-if="showActions">Acciones</th>
                    <tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="(itemIndex, item) in items" data-id="{{item.id}}">
                        <td ng-repeat="column in columns">
                            <div ng-class="column.cssClass"
                                 ng-if="column.$index">{{itemIndex + column.$indexOffset}}</div>

                            <div ng-class="column.cssClass"
                                 ng-if="!column.template && !column.templateUrl && !column.$index" bind-item
                                 bind-once="bindOnce" column="column" item-to-bind="item"></div>

                            <div ng-class="column.cssClass" ng-if="column.template || column.templateUrl" bind-template
                                 column="column" item-to-bind="item"></div>
                        </td>
                        <td style="white-space: nowrap; width: 1px;" ng-if="showActions">
                            <div class="btn-group">
                                <span>
                                    <a ng-show="allowDetails"
                                       class="view-link btn btn-default"
                                       ng-click="internalView(item)"><i class="fa fa-search"></i>
                                        Ver
                                    </a>
                                </span>
                                <span>
                                    <a ng-show="allowEdit || item.allowEdit"
                                       class="edit-link" ng-class="editClass"
                                       ng-click="internalEdit(item)">
                                        <i ng-class="editIcon"></i>
                                        {{editText}}

                                    </a>
                                </span>
                                <span>
                                    <a ng-show="allowDelete || item.allowDelete"
                                       class="delete-link" ng-class="deleteClass"
                                       ng-click="internalDelete(item, $index)">
                                        <i ng-class="deleteIcon"></i> {{deleteText}}
                                    </a>
                                </span>
                                <span>
                                    <a ng-repeat="customAction in customActions"
                                       tooltip="{{customAction.tooltip}}" class="btn btn-default "
                                       href="{{customAction.getLink(item) || '#'}}"
                                       ng-click="execCustomAction(customAction, item, itemIndex, $event)"
                                       ng-if="mustShowCustomAction(customAction, item)">
                                        <i class="{{customAction.icon}}"></i>
                                        <span class="hidden-sm">{{customAction.text}}</span>
                                    </a>
                                </span>
                                <input ng-show="allowCheck" type="checkbox"
                                       name="selected-{{$index}}" value="" ng-model="item.isChecked">
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="dt-row dt-bottom-row">
                <div class="col-sm-5">
                    <div class="dataTables_info" id="dt_basic_info">Total
                    registros: {{itemsCount}} / Total p&aacute;ginas: {{noOfPages}}</div>
                </div>

                <div class="col-sm-7 text-right">
                    <div class="dataTables_paginate paging_bootstrap_full">
                        <div ng-show="noOfPages > 1">
                            <pagination boundary-links="true" num-pages="noOfPages"
                                        ng-model="currentPage" max-size="paginationSize"
                                        items-per-page="pageSize" first-text="Inicio" last-text="Fin"
                                        next-text="Siguiente" total-items="itemsCount"
                                        previous-text="Anterior"
                                        on-select-page="internalPageChanged(page)"></pagination>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/ng-template" id="myModalContent">
    <div class="modal-body">
        <span>{{description}}</span>
    </div>

    <div class="modal-footer">
        <button class="btn btn-success" ng-click="ok()">OK</button>
        <button class="btn btn-danger" ng-click="cancel()">Cancelar</button>
    </div>
    </script>
</div>

