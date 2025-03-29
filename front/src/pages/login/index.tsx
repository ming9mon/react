import NaverLoginType1 from "./naverLogin/NaverLoginType1.tsx";
import NaverLoginType2 from "./naverLogin/NaverLoginType2.tsx";
import KakaoLoginType2 from "./kakaoLogin/KakaoLoginType2.tsx";
import KakaoLoginType1 from "./kakaoLogin/KakaoLoginType1.tsx";

const Login: React.FC = () => {


    return (
        <div>
            <h1>카카오 & 네이버 로그인 예제</h1>

            <h2>redirect 방식</h2>
            <h3>Type1 : Frontend에서 사용자 정보 받아오기</h3>
            <NaverLoginType1 />
            <KakaoLoginType1 />
            {/* 카카오 로그인 버튼 */}
            {/*<button onClick={handleKakaoLogin}>카카오 로그인</button>*/}

            {/* 네이버 로그인 버튼 */}
            <h3>Type2 : Backend에서 사용자 정보 받아오기</h3>
            <NaverLoginType2 />
            <KakaoLoginType2 />
        </div>
    )
};

// const Login = () => {
//     const REDIRECT_URI = "http://localhost:3000/naver-callback";
//     const STATE = "random_state_string"; // CSRF 방지용
//
//     // 카카오 로그인 URL
//     const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${KAKAO_CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
//
//     // 네이버 로그인 URL
//     const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?client_id=${NAVER_CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code&state=${STATE}`;
//
//     useEffect(() => {
//         // 네이버 로그인 SDK 로드
//         const script = document.createElement("script");
//         script.src = "https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js";
//         script.async = true;
//         script.onload = () => {
//             const naverLogin = new window.naver.NaverLogin();
//             naverLogin.init({
//                 clientId: "YOUR_NAVER_CLIENT_ID", // 네이버에서 받은 클라이언트 아이디
//                 callbackUrl: "YOUR_CALLBACK_URL", // 네이버 로그인 콜백 URL
//                 isPopup: true,
//                 loginButton: "naverLoginButton", // 네이버 로그인 버튼에 사용할 id
//             });
//         };
//         document.body.appendChild(script);
//     }, []);
//
//     const handleKakaoSuccess = (response: any) => {
//         console.log("카카오 로그인 성공:", response);
//     };
//
//     const handleKakaoFailure = (error: any) => {
//         console.error("카카오 로그인 실패:", error);
//     };
//
//     const handleNaverSuccess = (response: any) => {
//         console.log("네이버 로그인 성공:", response);
//     };
//
//     const handleNaverFailure = (error: any) => {
//         console.error("네이버 로그인 실패:", error);
//     };
//
//     return (
//         <div>
//             <h1>소셜 로그인</h1>
//             <KakaoLogin
//                 token={KAKAO_CLIENT_ID}
//                 onSuccess={handleKakaoSuccess}
//                 onFail={handleKakaoFailure}
//             />
//         </div>
//     );
// };

    // // 로그인 성공 시 실행될 콜백 함수
    // const handleSuccess = (response: any) => {
    //     console.log("로그인 성공:", response);
    // };
    //
    // // 로그인 실패 시 실행될 콜백 함수
    // const handleFailure = (error: any) => {
    //     console.error("로그인 실패:", error);
    // };
    //
    // return (
    //     <div>
    //         <h1>카카오 로그인 예제</h1>
    //         <KakaoLogin
    //             token={kakaoClientId}
    //             onSuccess={handleSuccess}
    //             onFail={handleFailure}
    //         />
    //         <div id="naverIdLogin" />
    //     </div>
    // );
// }

export default Login;