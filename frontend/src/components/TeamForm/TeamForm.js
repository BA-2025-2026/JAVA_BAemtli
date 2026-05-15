"use client";

import { useActionState, useEffect } from "react";
import { createUpdateTeam } from "@/actions/teamActions";
import styles from "../TeamSection/TeamSection.module.css";

export default function TeamForm({
  initialData = { id: null, name: "" },
  onSuccess,
  onCancel,
}) {
  const [state, formAction, isPending] = useActionState(createUpdateTeam, {
    id: initialData.id,
    success: false,
  });

  useEffect(() => {
    if (state?.success === true) {
      if (onSuccess) onSuccess();
    }
  }, [state?.success, onSuccess]);

  return (
    <form action={formAction} className={styles.editTeamForm} noValidate>
      <input type="hidden" name="id" value={initialData.id ?? ""} />
      <input
        name="teamName"
        autoFocus
        placeholder="Teamname eingeben..."
        defaultValue={state?.fields?.teamName ?? initialData.name}
        className={state?.errors?.teamName ? styles.inputError : ""}
      />
      <button type="submit" disabled={isPending}>
        <span className="material-symbols-outlined">
          {isPending ? "sync" : "check"}
        </span>
      </button>
      {onCancel && (
        <button type="button" onClick={onCancel}>
          <span className="material-symbols-outlined">close</span>
        </button>
      )}
      {state?.errors?.teamName && (
        <p className={styles.errorMessage}>{state.errors.teamName[0]}</p>
      )}
    </form>
  );
}
