import {useEffect, useRef} from "react";

declare global {
    interface Window {
        naver: any;
    }
}

const NaverLoginType1: React.FC = () => {
    const naverLoginRef = useRef<any>(null);
    const { naver } = window;

    useEffect(() => {
        if (!naver) return;
        const clientId = import.meta.env.VITE_NAVER_CLIENT_ID;
        const callbackUrl = import.meta.env.VITE_NAVER_TYPE2_CALLBACK_URL;

        // loginButton 설정을 제거하여 SDK가 별도의 DOM 요소를 찾지 않도록 함
        const naverLogin = new naver.LoginWithNaverId({
            clientId,
            callbackUrl,
            isPopup: false, // 리다이렉트 방식
            loginButton: { color: "green", type: 1, height: 47 },
        });

        naverLogin.init();

        naverLoginRef.current = naverLogin;
    }, []);

    return (
        <div id="naverIdLogin" />
    )
}
export default NaverLoginType1;