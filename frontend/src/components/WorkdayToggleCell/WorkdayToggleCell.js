"use client";

import { useActionState, useEffect, useMemo } from "react";
import { useRouter } from "next/navigation";
import { toggleWorkdayAction } from "@/actions/workdayActions";
import styles from "./WorkdayToggleCell.module.css";

export default function WorkdayToggleCell({
  date,
  dayLabel,
  isWorkday,
  isEmpty = false,
}) {
  const router = useRouter();
  const [state, formAction, isPending] = useActionState(toggleWorkdayAction, {
    success: false,
    status: "",
    date,
  });

  const disabled = isEmpty || isPending;
  const className = useMemo(() => {
    const classes = [
      styles.cellButton,
      isWorkday ? styles.workdayCell : styles.dayCell,
      isEmpty ? styles.emptyCell : "",
      isPending ? styles.pendingCell : "",
    ];

    return classes.filter(Boolean).join(" ");
  }, [isEmpty, isPending, isWorkday]);

  useEffect(() => {
    if (state?.success) {
      router.refresh();
    }
  }, [router, state?.success]);

  return (
    <form action={formAction} className={styles.form}>
      <input type="hidden" name="date" value={date ?? ""} />
      <button
        type="submit"
        className={className}
        disabled={disabled}
        aria-pressed={isWorkday}
        aria-label={isEmpty ? "Empty day" : `${dayLabel}${isWorkday ? ", workday" : ", not a workday"}`}
      >
        {dayLabel}
      </button>
    </form>
  );
}
