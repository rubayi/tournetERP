
export class CodeFormTableConfig {

    static columns() {
        return [
            {
                field: "edit",
                headerName: "관리",
                width: 100,
                flex: null,
                cellStyle: { 'justify-content': 'center' },
                cellRenderer: function (params) {
                    return `<div style="cursor: pointer; display: flex; justify-content: center;"><img src="/images/folder.svg" style="width: 20px; height: 20px;" /></div>`;
                },

            },
            {
                field: "codeUuid",
                headerName: "분류코드",
                sortable: true,
                filter: true,
            },
            {
                field: "codeEn",
                headerName: "코드명(영문)",
                sortable: true,
                filter: true,
            },
            {
                field: "codeKr",
                headerName: "코드명(한글)",
                sortable: true,
                filter: true,
            },
            {
                field: "codeValue",
                headerName: "코드값",
                sortable: true,
                filter: true,
            },
        ];
    }

}
