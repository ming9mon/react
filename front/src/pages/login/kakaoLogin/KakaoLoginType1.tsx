import {useEffect} from "react";

declare global {
    interface Window {
        Kakao: any;
    }
}

const KakaoLoginType1: React.FC = () => {
    const KAKAO_JS_KEY = import.meta.env.VITE_KAKAO_JS_KEY;
    const KAKAO_REDIRECT_URL = import.meta.env.VITE_KAKAO_REDIRECT_URI_TYPE1;

    useEffect(() => {
        if (window.Kakao && !window.Kakao.isInitialized()) {
            window.Kakao.init(KAKAO_JS_KEY);
        }
    }, []);

    const handleLogin = () => {
        if (!window.Kakao) {
            console.error('Kakao SDK가 로드되지 않았습니다.');
            return;
        }

        window.Kakao.Auth.authorize({
            redirectUri: KAKAO_REDIRECT_URL,
            //state: 'YOUR_CUSTOM_STATE', // 선택 사항: 추가 파라미터 전달 시 사용
            scope: 'profile_nickname, account_email', // 선택 사항: 추가 동의 항목 요청 시 사용
        });
    };

    return (
        <button onClick={handleLogin}>카카오 로그인</button>
    );
}

export default KakaoLoginType1;