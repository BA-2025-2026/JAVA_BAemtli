"use client";

import { useActionState, useEffect } from "react";
import { createUpdateChoreCategory } from "@/actions/choreCategoryActions";
import styles from "./ChoreCategoryForm.module.css";

export default function ChoreCategoryForm({
  initialData = { id: null, name: "", description: "" },
  onSuccess,
  onCancel,
}) {
  const [state, formAction, isPending] = useActionState(
    createUpdateChoreCategory,
    {
      id: initialData.id,
      success: false,
    },
  );

  useEffect(() => {
    if (state?.success === true) {
      if (onSuccess) onSuccess();
    }
  }, [state?.success, onSuccess]);

  return (
    <form action={formAction} className={styles.form} noValidate>
      <input type="hidden" name="id" value={initialData.id ?? ""} />
      <div className={styles.fields}>
        <label>
          <span>Titel</span>
          <input
            name="name"
            autoFocus
            placeholder="Titel eingeben..."
            defaultValue={state?.fields?.name ?? initialData.name}
            className={state?.errors?.name ? styles.inputError : ""}
          />
        </label>
        <label>
          <span>Beschreibung</span>
          <textarea
            name="description"
            placeholder="Beschreibung eingeben..."
            defaultValue={
              state?.fields?.description ?? initialData.description
            }
          />
        </label>
      </div>
      <div className={styles.actions}>
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
      {state?.errors?.name && (
        <p className={styles.errorMessage}>{state.errors.name[0]}</p>
      )}
      {state?.message && <p className={styles.errorMessage}>{state.message}</p>}
    </form>
  );
}
