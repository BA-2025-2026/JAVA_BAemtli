"use client";

import { useState } from "react";
import { deleteChoreCategoryAction } from "@/actions/choreCategoryActions";
import DeleteModal from "../DeleteModal/DeleteModal";
import ChoreCategoryForm from "../ChoreCategoryForm/ChoreCategoryForm";
import styles from "./ChoreCategoryCard.module.css";

export default function ChoreCategoryCard({ choreCategory }) {
  // Delete Chore Category Confirmation Modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  // Edit Chore Category State
  const [isEditing, setIsEditing] = useState(false);

  // Delete chore category handler
  const handleDeleteClick = () => setIsModalOpen(true);

  // Delete chore category confirmation modal handler
  const handleConfirmDelete = async () => {
    await deleteChoreCategoryAction(choreCategory.id);
    setIsModalOpen(false);
  };

  return (
    <>
      <DeleteModal
        isOpen={isModalOpen}
        entityName={choreCategory.name}
        onCancel={() => setIsModalOpen(false)}
        onConfirm={handleConfirmDelete}
      />
      <div className={styles.choreCategoryCard}>
        {!isEditing ? (
          <>
            <div className={styles.content}>
              <h4>{choreCategory.name}</h4>
              <div className={styles.descriptionBlock}>
                <p className={styles.label}>Beschreibung</p>
                <p>{choreCategory.description}</p>
              </div>
            </div>
            <div className={styles.crudButtons}>
              <span
                className="material-symbols-outlined"
                onClick={() => setIsEditing(true)}
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
          <ChoreCategoryForm
            initialData={{
              id: choreCategory.id,
              name: choreCategory.name,
              description: choreCategory.description,
            }}
            onSuccess={() => setIsEditing(false)}
            onCancel={() => setIsEditing(false)}
          />
        )}
      </div>
    </>
  );
}
