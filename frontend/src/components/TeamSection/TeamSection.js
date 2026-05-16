"use client";

import { useState } from "react";
import { deleteTeamAction } from "@/actions/teamActions";
import DeleteModal from "../DeleteModal/DeleteModal";
import TeamForm from "../TeamForm/TeamForm";
import styles from "./TeamSection.module.css";
import TraineesSection from "../TraineesSection/TraineesSection";

export default function TeamSection({ team }) {
  // Delete Team Confirmation Modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  // Edit Team State
  const [isEditingTeam, setIsEditingTeam] = useState(false);

  // Delete team handler
  const handleDeleteClick = () => setIsModalOpen(true);

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
                onClick={() => setIsEditingTeam(true)}
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
          <TeamForm
            initialData={{ id: team.id, name: team.name }}
            onSuccess={() => setIsEditingTeam(false)}
            onCancel={() => setIsEditingTeam(false)}
          />
        )}
      </div>
      <TraineesSection teamId={team.id} trainees={team.trainees} />
    </>
  );
}
