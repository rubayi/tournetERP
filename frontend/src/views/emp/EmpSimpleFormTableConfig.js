export class EmpFormTableConfig {

    static columns() {
        return [
            {
                field: "edit",
                headerName: "관리",
                width: 100,
                flex: null,
                cellStyle: { 'justify-content': 'center' },
                cellRenderer: function (params) {
                    return `<div style="display: flex; justify-content: center;"><img src="images/folder.svg" style="width: 20px; height: 20px;" /></div>`;
                },

            },
            {
                field: "empUuid",
                headerName: "번호",
                width: 100,
                flex: null,
                sortable: true,
                filter: true,
                // checkboxSelection: true,
            },
            {
                field: "username",
                headerName: "사용자명",
                width: 110,
                sortable: true,
                filter: true,
            },
            {
                field: "empKor",
                headerName: "직원명",
                width: 120,
                sortable: true,
                filter: true,
            },
            {
                field: "empEng",
                headerName: "직원명(영문)",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "empEmail",
                headerName: "이메일",
                sortable: true,
                filter: true,
            },

            {
                field: "empPhone",
                headerName: "전화번호",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "empWorkPhone",
                headerName: "내선번호",
                sortable: true,
                filter: true,
            },
            {
                field: "empEmailBook",
                headerName: "예약이메일",
                sortable: true,
                filter: true,
            },
            {
                field: "empAddress1",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empAddress2",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empCity",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empState",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empCountry",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empZip",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empDob",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empDobType",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empMemo",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empStatus",
                headerName: "주소",
                hide: true,
            },
            {
                field: "empEmailBook",
                headerName: "주소",
                hide: true,
                resizable: false
            },
        ];
    }
}
