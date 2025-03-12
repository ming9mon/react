import React from "react";
import {Route, Routes} from "react-router-dom";
import Index from "../pages";
import EmptyLayout from "../layout/EmptyLayout.tsx";
import DefaultLayout from "../layout/DefaultLayout.tsx";

const Router: React.FC = () => {
    return (
        <div className="">
            <Routes>
                <Route element={<EmptyLayout />}>
                    <Route path="/" element={<Index />} />
                </Route>

                <Route element={<DefaultLayout />}>

                </Route>
            </Routes>
        </div>
    )
}

export default Router;