import api from "./api";
import authHeader from "src/services/auth-header";
class UploadFilesService {
    upload(file, onUploadProgress) {
        let formData = new FormData();

        formData.append("file", file);

        return api.post("/file/upload", formData, {
            headers: {
                "Content-Type": "multipart/form-data"
                , Authorization: authHeader()},
            onUploadProgress
        });
    }

    getFiles() {
        return api.get("/file/files");
    }
}

export default new UploadFilesService();
