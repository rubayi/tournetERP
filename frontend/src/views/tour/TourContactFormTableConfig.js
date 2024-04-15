export class TrContactFormTableConfig {

    static columns() {
        return [
            {
                field: "edit",
                headerName: "관리",
                width: 90,
                flex: null,
                cellStyle: { 'justify-content': 'center' },
                cellRenderer: function (params) {
                    return `<div style="cursor: pointer; display: flex; justify-content: center;"><img src="images/folder.svg" style="width: 20px; height: 20px;" /></div>`;
                },

            },
            {
                field: "contactUuid",
                headerName: "ID번호",
                width: 100,
                sortable: true,
                filter: true,
            },
            {
                field: "contactTypeName",
                headerName: "유형",
                sortable: true,
                filter: true,
            },
            {
                field: "contactCont",
                headerName: "연락처",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "repYnName",
                headerName: "대표연락처여부",
                width: 150,
                sortable: true,
                filter: true,
            },
        ];
    }
}
