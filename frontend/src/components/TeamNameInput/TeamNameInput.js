"use client";
import { useState } from "react";
import styles from "./TeamNameInput.module.css";

export default function TeamNameInput({ initialValue = "", onSave, onCancel }) {
  const [value, setValue] = useState(initialValue);
  const [fieldError, setFieldError] = useState(null);

  const handleSave = async () => {
    setFieldError(null);

    const trimmed = value.trim();
    if (!trimmed) {
      setFieldError("Name darf nicht leer sein");
      return;
    }
    if (trimmed.length < 2 || trimmed.length > 30) {
      setFieldError("Name muss zwischen 2 und 30 Zeichen lang sein");
      return;
    }

    // onSave gibt entweder null (Erfolg) oder einen Error-String zurück
    const error = await onSave(trimmed);
    if (error) {
      setFieldError(error);
    }
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.row}>
        <input
          type="text"
          value={value}
          onChange={(e) => {
            setValue(e.target.value);
            setFieldError(null);
          }}
          onKeyDown={(e) => e.key === "Enter" && handleSave()}
          className={fieldError ? styles.inputError : ""}
          placeholder="Teamname"
          autoFocus
        />
        <button onClick={handleSave}>
          <span className="material-symbols-outlined">check</span>
        </button>
        <button onClick={onCancel}>
          <span className="material-symbols-outlined">close</span>
        </button>
      </div>
      {fieldError && <p className={styles.errorText}>{fieldError}</p>}
    </div>
  );
}
