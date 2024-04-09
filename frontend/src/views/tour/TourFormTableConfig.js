import * as moment from "moment/moment";

export class tourInfoFormTableConfig {

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
                field: "tourUuid",
                headerName: "번호",
                width: 100,
                flex: null,
                sortable: true,
                filter: true,
                // checkboxSelection: true,
            },
            {
                field: "tourCategoryName",
                headerName: "투어구분",
                hide: true,
            },
            {
                field: "tourKor + tourEng",
                headerName: "투어명",
                width: 120,
                sortable: true,
                filter: true,
            },
            {
                field: "tourAreaName + tourAreaSubName",
                headerName: "지역",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "prepaidMethodName",
                headerName: "프리페이드지정",
                width: 150,
                sortable: true,
                filter: true,
            },

            {
                field: "tourMemo",
                headerName: "주소",
                hide: true,
            },
            {
                field: "tourStatus",
                headerName: "주소",
                hide: true,
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
