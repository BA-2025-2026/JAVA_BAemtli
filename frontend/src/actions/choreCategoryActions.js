"use server";

import ChoreCategoriesAPI from "@/lib/api/ChoreCategories";
import { revalidatePath } from "next/cache";
import { z } from "zod";

const choreCategoryValidationSchema = z.object({
  name: z
    .string()
    .max(30, "Titel darf maximal 30 Zeichen lang sein.")
    .min(1, "Titel darf nicht leer sein."),
  description: z
    .string()
    .min(1, "Beschreibung darf nicht leer sein.")
    .max(1000, "Beschreibung darf maximal 30 Zeichen lang sein."),
});

export async function createUpdateChoreCategory(prevState, formData) {
  // Verify Session

  // Parse and Validate
  const data = Object.fromEntries(formData);
  const validated = choreCategoryValidationSchema.safeParse(data);

  // Case: Validation error
  if (!validated.success) {
    return {
      id: prevState?.id,
      fields: data,
      errors: validated.error.flatten().fieldErrors,
      success: false,
    };
  }

  try {
    const { name, description } = validated.data;

    if (prevState?.id) {
      // UPDATE (PATCH)
      await ChoreCategoriesAPI.update(prevState.id, { name, description });
    } else {
      // CREATE
      await ChoreCategoriesAPI.create({ name, description });
    }

    // Update cache so UI will show changes
    revalidatePath("/chorecategories");

    // Return success
    return { success: true, errors: {}, fields: {} };
  } catch (error) {
    let errorMessage = "An unexpected error occurred.";

    // Evaluate 4xx client errors
    if (error.response >= 400 && error.response < 500) {
      errorMessage = "Invalid input data. Please check entry fields.";
    }
    // Evaluate 5xx server errors
    else if (error.response >= 500) {
      errorMessage = "Server error. Please try again later.";
    }

    return {
      success: false,
      message: errorMessage,
    };
  }
}

export async function deleteChoreCategoryAction(id) {
  // Verify session

  // Delete Chore Category (hand in JWT Token later)
  await ChoreCategoriesAPI.delete(id);
  revalidatePath("/chorecategories");
}
