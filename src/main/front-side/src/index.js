import React from 'react';
import App from './App';
import { createRoot } from 'react-dom/client';
import {createElementByTagName, injectElement} from "html-evaluate-utils/Utils";

const rootElementId = "labas_as_krabas_root";
injectElement('//body', createElementByTagName("div", rootElementId));
const rootElement = document.getElementById(rootElementId);

const app = (
        <App />
);

// Naujas būdas su createRoot
const root = createRoot(rootElement);
root.render(app);

