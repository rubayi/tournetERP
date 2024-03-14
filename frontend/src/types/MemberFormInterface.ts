// MemberFormInterface.ts
import { MemberPermissionForm } from "@/types/MemberPermissionForm";

export interface MemberFormInterface {
    empUuid: number | null;
    username: string | null;
    empEng: string | null;
    empKor: string | null;
    empWorkType: string | null;
    empDiv: string | null;
    password: string | null;
    empEmail: string | null;
    empStatus: string | null;
    email: string | null;
    empEmailBook: string | null;
    empPhone: string | null;
    empWorkPhone: string | null;
    empDob: string | null;
    empDobType: string | false;
    empMemo: string | null;
    empAddress1: string | null;
    empAddress2: string | null;
    empState: string | null;
    empCountry: string | null;
    empZip: string | null;
    empTitle: string | null;
    empRole: string | null;
    securityForm: MemberPermissionForm;
}
