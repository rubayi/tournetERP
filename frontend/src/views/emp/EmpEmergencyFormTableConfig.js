export class EmpEmergencyFormTableConfig {

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
                field: "emergencyUuid",
                headerName: "ID번호",
                width: 100,
                sortable: true,
                filter: true,
            },
            {
                field: "emerName",
                headerName: "이름",
                sortable: true,
                filter: true,
            },
            {
                field: "emerRelation",
                headerName: "관계",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "emerCity",
                headerName: "주소(도시)",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "emerPhone1",
                headerName: "전화번호",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "emerPhone2",
                headerName: "전화번호2",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "emerWorkPhone",
                headerName: "직장전화",
                width: 150,
                sortable: true,
                filter: true,
            },

        ];
    }
}
