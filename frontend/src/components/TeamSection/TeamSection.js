"use client";

import { useActionState, useState } from "react";
import TraineeCard from "../TraineeCard/TraineeCard";
import DeleteModal from "../DeleteModal/DeleteModal";
import { createUpdateTeam, deleteTeamAction } from "@/actions/teamActions";
import styles from "./TeamSection.module.css";
import { useEffect } from "react";

export default function TeamSection({ team }) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditingTeam, setIsEditingTeam] = useState(false);

  const [state, formAction, isPending] = useActionState(createUpdateTeam, {
    id: team.id,
    success: false,
  });

  // If editing team was successful, leave editing state
  useEffect(() => {
    if (state?.success === true) {
      setIsEditingTeam(false);
    }
  }, [state?.success, state]);

  const handleStartEdit = () => setIsEditingTeam(true);
  const handleCancelEdit = () => setIsEditingTeam(false);

  // Delete team handler
  const handleDeleteClick = () => {
    setIsModalOpen(true);
  };

  // Delete team confirmation modal handler
  const handleConfirmDelete = async () => {
    await deleteTeamAction(team.id);
    setIsModalOpen(false);
  };

  return (
    <>
      <DeleteModal
        isOpen={isModalOpen}
        entityName={team.name}
        onCancel={() => setIsModalOpen(false)}
        onConfirm={handleConfirmDelete}
      />
      <div className={styles.teamTitleCard}>
        {!isEditingTeam ? (
          <>
            <h4>{team.name}</h4>
            <div className={styles.crudButtons}>
              <span
                className="material-symbols-outlined"
                onClick={handleStartEdit}
              >
                edit
              </span>
              <span
                className="material-symbols-outlined"
                onClick={handleDeleteClick}
              >
                delete
              </span>
            </div>
          </>
        ) : (
          <>
            <form
              key={isEditingTeam ? "editing" : "idle"}
              action={formAction}
              noValidate
              className={styles.editTeamForm}
            >
              <input type="hidden" name="id" value={team.id} />
              <input
                name="teamName"
                autoFocus
                defaultValue={state?.fields?.teamName ?? team.name}
                className={state?.errors?.teamName ? styles.inputError : ""}
              />
              <button type="submit" disabled={isPending}>
                <span className="material-symbols-outlined">
                  {isPending ? "sync" : "check"}
                </span>
              </button>
              <button type="button" onClick={handleCancelEdit}>
                <span className="material-symbols-outlined">close</span>
              </button>
              {/* Form Error Message */}
              {state?.errors?.teamName && (
                <p className={styles.errorMessage}>
                  {state.errors.teamName[0]}
                </p>
              )}
            </form>
          </>
        )}
      </div>
      <div className={styles.traineeList}>
        {team.trainees.map((trainee) => (
          <TraineeCard key={trainee.id} trainee={trainee} />
        ))}
        {team.trainees.length === 0 && (
          <div className={`${styles.teamTitleCard} ${styles.noEntityInfo}`}>
            <h4>Noch keine Lernenden vorhanden.</h4>
          </div>
        )}
        <div className={styles.addTraineeSection}>
          <div>
            <h4>Lernende*r</h4>
            <h4>hinzufügen</h4>
          </div>
          <span className="material-symbols-outlined">add</span>
        </div>
      </div>
    </>
  );
}
