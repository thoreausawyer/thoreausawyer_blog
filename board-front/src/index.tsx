import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { BrowserRouter } from "react-router-dom";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    {/* React-Router-Dom SPA 작업 BrowserRouter로 감싼다 */}
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);
