"use client";
import { useActionState, useState } from "react";
import styles from "./EditableNameField.module.css";
import { teamNameActions } from "@/actions/teamNameActions";

export default function EditableNameField({
  initialValue = "",
  onSave,
  onCancel,
}) {
  const [state, action, pending] = useActionState(teamNameActions, null);

  return (
    <div className={styles.wrapper}>
      <form className={styles.row}>
        <input
          type="text"
          name="name"
          className={fieldError ? styles.inputError : ""}
          placeholder="Teamname"
          autoFocus
        />
        <button type="submit" disabled={pending}>
          <span className="material-symbols-outlined">check</span>
        </button>
        <button onClick={onCancel} disabled={pending}>
          <span className="material-symbols-outlined">close</span>
        </button>
      </form>
      {state?.message && <p className={styles.errorText}>{state.message}</p>}
    </div>
  );
}
