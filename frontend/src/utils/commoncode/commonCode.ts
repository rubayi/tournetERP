/* eslint-disable @typescript-eslint/no-explicit-any */
// commonModule.ts
//import { ref } from 'vue'; // Import ref if you're using Vue 3
import { CodeService } from 'src/services/CodeService';
import { SelectOption } from 'src/types/SelectOption';

export function loadOptionsList(
  groupCode: number,
  targetList: any,
  locale: string
) {
  CodeService.getGroupCodeForm(groupCode).then((response) => {
    if (Array.isArray(response)) {
      targetList.value = response.map((x) => {
        return new SelectOption(
          locale === 'en' ? x.codeEn : x.codeKr,
          x.codeUuid
        );
      });
    } else {
      console.error('Response is not an array:', response);
    }
  });
}
