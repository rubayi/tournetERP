
export class EmpFormTableConfig {
  static overlay =
    '<span class="ag-overlay-loading-center">Please wait while your Data are loading</span>';
  static columns = [
      {
          field: "empUuid",
          headerName: "번호",
          width: 50,
          sortable: true,
          filter: true,
          // checkboxSelection: true,
      },
      {
          field: "edit",
          headerName: "관리",
          width: 100,
          cellRenderer: function (params) {
              return `<q-btn style="background: #50d427ad; padding: 5px; border-radius: 4px; cursor: pointer; font-size: 12px;">
                    ${params.value}</q-btn>`;
          },
          valueGetter: function (params) {
              return "수정/삭제";
          },
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
          field: "empDiv",
          headerName: "부서명",
          width: 100,
          sortable: true,
          filter: true,
          cellRenderer: function (params) {
              return `${params.value}`;
          },
          valueGetter: function (params) {
              return this.getValueName(params.data.empDiv, this.divOptions)
          }.bind(this)
      },
      {
          field: "empTitle",
          headerName: "직위",
          width: 100,
          sortable: true,
          filter: true,
          cellRenderer: function (params) {
              return `${params.value}`;
          },
          valueGetter: function (params) {
              return this.getValueName(params.data.empDiv, this.titleOptions)
          }.bind(this)
      },
      {
          field: "empRole",
          headerName: "직책",
          width: 100,
          sortable: true,
          filter: true,
          cellRenderer: function (params) {
              return `${params.value}`;
          },
          valueGetter: function (params) {
              return this.getValueName(params.data.empDiv, this.empRoleOptions)
          }.bind(this)
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
      },
  ];

}
