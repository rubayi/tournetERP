import { UserDetails } from 'src/types/UserDetails';
class TokenService {
    getLocalRefreshToken(): string | undefined {
        const user = JSON.parse(localStorage.getItem("user") || "");
        return user?.refreshToken;
    }

    getLocalAccessToken(): string | undefined {
        const user = JSON.parse(localStorage.getItem("user") || "");
        return user?.accessToken;
    }

    updateLocalAccessToken(token: string): void {
        const user = JSON.parse(localStorage.getItem("user") || "");
        user.accessToken = token;
        localStorage.setItem("user", JSON.stringify(user));
    }

    getUser(): UserDetails {
        return JSON.parse(localStorage.getItem("user") || "");
    }

    setUser(user: UserDetails): void {
        localStorage.setItem("user", JSON.stringify(user));
    }

    removeUser(): void {
        localStorage.removeItem("user");
    }
}

export default new TokenService();
