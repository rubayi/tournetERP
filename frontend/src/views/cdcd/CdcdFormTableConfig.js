import * as moment from 'moment';
export class cdcdFormTableConfig {

    static columns() {
        return [
            {
                field: "edit",
                headerName: "관리",
                width: 100,
                flex: null,
                cellStyle: { 'justify-content': 'center' },
                cellRenderer: function (params) {
                    return `<div style="cursor: pointer; display: flex; justify-content: center;"><img src="images/folder.svg" style="width: 20px; height: 20px;" /></div>`;
                },

            },
            {
                field: "cdCdUuid",
                headerName: "번호",
                width: 100,
                flex: null,
                sortable: true,
                filter: true,
                // checkboxSelection: true,
            },
            {
                field: "mngNameKor",
                headerName: "관리명",
                width: 120,
                sortable: true,
                filter: true,
            },
            {
                field: "mngNameEng",
                headerName: "관리명(영문)",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "nameOnCard",
                headerName: "명의자",
                sortable: true,
                filter: true,
            },

            {
                field: "expMonth",
                headerName: "유효일달",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
              field: "expYear",
              headerName: "유효일년",
              width: 150,
              sortable: true,
              filter: true,
            },
            {
              field: "beginDt",
              headerName: "시작일",
              width: 150,
              sortable: true,
              filter: true,
            },
            {
                field: "endDt",
                headerName: "종료일",
                sortable: true,
                filter: true,
            },
            {
                field: "createdByName",
                headerName: "등록자",
                sortable: true,
                filter: true,
            },
            {
                field: "createdDt",
                headerName: "등록일",
                sortable: true,
                filter: true,
                cellRenderer: (createdDt) => {
                  return moment(createdDt.value).format('YYYY/MM/DD HH:mm')
                }
            },

        ];
    }
}
