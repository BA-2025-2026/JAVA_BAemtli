"use client";

import { useState } from "react";
import styles from "./TraineesSection.module.css";
import TraineeCard from "../TraineeCard/TraineeCard";
import TraineeForm from "../TraineeForm/TraineeForm";

export default function TraineeSection({ teamId, trainees }) {
  // Show add new trainee form
  const [isAdding, setIsAdding] = useState(false);

  return (
    <div className={styles.traineeList}>
      {trainees.map((trainee) => (
        <TraineeCard key={trainee.id} trainee={trainee} />
      ))}
      {trainees.length === 0 && (
        <div className="noEntityInfo">
          <p>Noch keine Lernenden vorhanden.</p>
        </div>
      )}
      {!isAdding ? (
        <div
          className={styles.addTraineeSection}
          onClick={() => setIsAdding(true)}
        >
          <div>
            <h4>Lernende*r</h4>
            <h4>hinzufügen</h4>
          </div>
          <span className="material-symbols-outlined">add</span>
        </div>
      ) : (
        <TraineeForm
          initialData={{ teamId: teamId }}
          onSuccess={() => setIsAdding(false)}
          onCancel={() => setIsAdding(false)}
        />
      )}
    </div>
  );
}
