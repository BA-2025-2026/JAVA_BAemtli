"use client";

import { useActionState, useEffect } from "react";
import { createUpdateTrainee } from "@/actions/traineeActions";
import styles from "../TraineesSection/TraineesSection.module.css";

export default function TraineeForm({
  initialData = { id: null, teamId: null, firstName: "", lastName: "" },
  onSuccess,
  onCancel,
}) {
  const [state, formAction, isPending] = useActionState(createUpdateTrainee, {
    id: initialData.id,
    success: false,
  });

  useEffect(() => {
    if (state?.success === true) {
      if (onSuccess) onSuccess();
    }
  }, [state?.success, onSuccess]);

  return (
    <form action={formAction} className={styles.editTraineeForm} noValidate>
      <div className={styles.inputAndButtonWrapper}>
        <div className={styles.inputSection}>
          <input type="hidden" name="id" value={initialData.id ?? ""} />
          <input type="hidden" name="teamId" value={initialData.teamId ?? ""} />
          <input
            name="firstName"
            autoFocus
            placeholder="Vorname"
            defaultValue={state?.fields?.firstName ?? initialData.firstName}
            className={state?.errors?.firstName ? styles.inputError : ""}
          />
          <input
            name="lastName"
            placeholder="Nachname"
            defaultValue={state?.fields?.lastName ?? initialData.lastName}
            className={state?.errors?.lastName ? styles.inputError : ""}
          />
        </div>
        <div className={styles.buttonSection}>
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
        </div>
      </div>
      {state?.errors?.firstName && (
        <p className={styles.errorMessage}>{state.errors.firstName[0]}</p>
      )}
      {state?.errors?.lastName && (
        <p className={styles.errorMessage}>{state.errors.lastName[0]}</p>
      )}
    </form>
  );
}
