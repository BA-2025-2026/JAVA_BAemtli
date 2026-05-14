"use client";
import { useState } from "react";
import styles from "./DeleteModal.module.css";

export default function DeleteModal({
  isOpen,
  entityName,
  onConfirm,
  onCancel,
}) {
  const [inputValue, setInputValue] = useState("");

  // Prevent showing accidentally
  if (!isOpen) return null;

  // User typed confirmation sentence correctly
  const isMatch = inputValue === entityName;

  const handleConfirm = () => {
    if (isMatch) {
      onConfirm();
      setInputValue("");
    }
  };

  const handleCancel = () => {
    setInputValue("");
    onCancel();
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2>
          Möchtest du <span className={styles.highlight}> {entityName} </span>{" "}
          und alle verbundenen Daten wirklich löschen?
        </h2>
        <p>
          Wenn du <span className={styles.highlight}> {entityName} </span>{" "}
          löschst, werden auch alle damit verbundenen Entitäten und Daten
          dauerhaft gelöscht (ON DELETE CASCADE).
        </p>
        <p>
          Diese Aktion kann <span className={styles.highlight}> nicht </span>
          rückgängig gemacht werden.
        </p>
        <p>
          Bitte tippe den Namen
          <span className={styles.highlight}> {entityName} </span>
          ein, um das Löschen zu bestätigen.
        </p>
        <input
          type="text"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          placeholder={entityName}
          className={styles.input}
        />
        <div className={styles.actions}>
          <button onClick={handleCancel} className={styles.cancelBtn}>
            Abbrechen
          </button>
          <button
            onClick={handleConfirm}
            disabled={!isMatch}
            className={styles.deleteBtn}
          >
            Löschen
          </button>
        </div>
      </div>
    </div>
  );
}
