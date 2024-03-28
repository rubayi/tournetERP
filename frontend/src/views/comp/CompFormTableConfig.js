export class compFormTableConfig {

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
                field: "compUuid",
                headerName: "번호",
                width: 100,
                flex: null,
                sortable: true,
                filter: true,
                // checkboxSelection: true,
            },
            {
                field: "compKor",
                headerName: "업체명",
                width: 120,
                sortable: true,
                filter: true,
            },
            {
                field: "compEng",
                headerName: "업체명(영문)",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
                field: "compSectorName",
                headerName: "유형",
                sortable: true,
                filter: true,
            },

            {
                field: "compPhone",
                headerName: "전화번호",
                width: 150,
                sortable: true,
                filter: true,
            },
            {
            field: "compDivName",
            headerName: "부서",
            width: 150,
            sortable: true,
            filter: true,
            },
            {
              field: "compStatusName",
              headerName: "상태",
              width: 150,
              sortable: true,
              filter: true,
            },
            {
                field: "compWorkPhone",
                headerName: "내선번호",
                sortable: true,
                filter: true,
            },
            {
                field: "compEmailBook",
                headerName: "예약이메일",
                hide: true,
            },
            {
                field: "compAddress1",
                headerName: "주소1",
                hide: true,
            },
            {
                field: "compAddress2",
                headerName: "주소2",
                hide: true,
            },
            {
                field: "compCity",
                headerName: "도시",
                hide: true,
            },
            {
                field: "compState",
                headerName: "주/도",
                hide: true,
            },
            {
                field: "compCountry",
                headerName: "국가",
                hide: true,
            },
            {
                field: "compZip",
                headerName: "주소",
                hide: true,
            },
            {
                field: "compDob",
                headerName: "생일",
                hide: true,
            },
            {
              field: "compDobType",
              headerName: "생일타입",
              hide: true,
            },
            {
                field: "compMemo",
                headerName: "주소",
                hide: true,
            },
            {
                field: "compStatus",
                headerName: "주소",
                hide: true,
            },

        ];
    }
}
