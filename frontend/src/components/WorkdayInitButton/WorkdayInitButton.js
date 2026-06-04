"use client";

import { useActionState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { initializeCurrentSchoolYearAction } from "@/actions/workdayActions";
import styles from "./WorkdayInitButton.module.css";

export default function WorkdayInitButton() {
  const router = useRouter();
  const [state, formAction, isPending] = useActionState(
    initializeCurrentSchoolYearAction,
    {
      success: false,
      status: "",
    },
  );

  useEffect(() => {
    if (state?.success) {
      // The server action already revalidates the route; this keeps the UI fresh.
      router.refresh();
    }
  }, [router, state?.success]);

  return (
    <form action={formAction} className={styles.wrapper}>
      <button className={styles.initButton} type="submit" disabled={isPending}>
        <span className="material-symbols-outlined">
          {isPending ? "sync" : "add"}
        </span>
        <span className={styles.initButtonText}>
          Ausbildungsjahr initialisieren
        </span>
      </button>
      <p className={styles.actionHint}>
        Für das ganze Ausbildungsjahr werden <br /> Mi bis Fr als Arbeitstag
        eingetragen.
      </p>
    </form>
  );
}
