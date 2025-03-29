import {useRef} from "react";

declare global {
    interface Window {
        naver: any;
    }
}

const NaverLogin: React.FC = () => {
    // const naverLoginRef = useRef<any>(null);
    const { naver } = window;

    // useEffect(() => {
    //     if (!naver) return;
    //     const naverOauthUrl = import.meta.env.VITE_NAVER_OAUTH_URL
    //     const clientId = import.meta.env.VITE_NAVER_CLIENT_ID;
    //     const callbackUrl = import.meta.env.VITE_NAVER_CALLBACK_URL;
    //     const state = Math.random().toString(36).substring(2, 15)
    //
    //     // loginButton 설정을 제거하여 SDK가 별도의 DOM 요소를 찾지 않도록 함
    //     const naverLogin = new naver.LoginWithNaverId({
    //         clientId,
    //         callbackUrl,
    //         isPopup: false, // 리다이렉트 방식 로그인 (팝업이 아님)
    //     });
    //
    //     naverLogin.init();
    //     // 생성된 인스턴스를 ref에 저장
    //     naverLoginRef.current = naverLogin;
    // }, []);
    //
    // const handleNaverLogin = () => {
    //     if (naverLoginRef.current) {
    //         naverLoginRef.current.authorize();
    //     }
    // };

    const handleNaverLogin = () => {
        if (!naver) return;
        const naverOauthUrl = import.meta.env.VITE_NAVER_OAUTH_URL
        const clientId = import.meta.env.VITE_NAVER_CLIENT_ID;
        const callbackUrl = import.meta.env.VITE_NAVER_CALLBACK_URL;
        const state = Math.random().toString(36).substring(2, 15)

        window.location.href = `${naverOauthUrl}?response_type=code&client_id=${clientId}&redirect_uri=${callbackUrl}&state=${state}`
    }

    return (
        <div>
            <button onClick={handleNaverLogin}>
                네이버 로그인
            </button>
        </div>
    )
}
export default NaverLogin;