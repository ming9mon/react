import React from "react";
import {Route, Routes} from "react-router-dom";
import Index from "../pages";
import EmptyLayout from "../layout/EmptyLayout.tsx";
import DefaultLayout from "../layout/DefaultLayout.tsx";
import Login from "../pages/login";
import NaverLoginType1Callback from "../pages/login/naverLogin/NaverLoginType1Callback.tsx";
import NaverLoginType2Callback from "../pages/login/naverLogin/NaverLoginType2Callback.tsx";
import KakaoLoginType1Cabllback from "../pages/login/kakaoLogin/KakaoLoginType1Cabllback.tsx";
import KakaoLoginType2Cabllback from "../pages/login/kakaoLogin/KakaoLoginType2Cabllback.tsx";

const Router: React.FC = () => {
    return (
        <div className="">
            <Routes>
                <Route element={<EmptyLayout />}>
                    <Route path="/" element={<Index />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/login/naverType1Callback" element={<NaverLoginType1Callback />} />
                    <Route path="/login/naverType2Callback" element={<NaverLoginType2Callback />} />
                    <Route path="/login/kakaoType1Callback" element={<KakaoLoginType1Cabllback />} />
                    <Route path="/login/kakaoType2Callback" element={<KakaoLoginType2Cabllback />} />
                </Route>

                <Route element={<DefaultLayout />}>

                </Route>
            </Routes>
        </div>
    )
}

export default Router;