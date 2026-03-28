"use client";
import { createContext, useContext, useState, useCallback } from "react";
import styles from "./ErrorToast.module.css";

const ErrorContext = createContext();

// This reference allows api.js (outside of react) to trigger the toast
let globalShowError = null;

export function getGlobalShowError() {
  return globalShowError;
}

export function ErrorProvider({ children }) {
  const [error, setError] = useState(null);

  const showError = useCallback((message) => {
    setError(message);
    setTimeout(() => setError(null), 5000);
  }, []);

  // Set singleton when component mounts
  globalShowError = showError;

  return (
    <ErrorContext.Provider value={{ showError }}>
      {children}
      {error && (
        <div className={styles.toast}>
          <span className="material-symbols-outlined">error</span>
          <p>{error}</p>
          <button onClick={() => setError(null)}>
            <span className="material-symbols-outlined">close</span>
          </button>
        </div>
      )}
    </ErrorContext.Provider>
  );
}

export const useError = () => useContext(ErrorContext);
