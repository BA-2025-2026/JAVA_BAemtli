"use server";

import WorkdaysAPI from "@/lib/api/Workdays";
import { revalidatePath } from "next/cache";

export async function initializeCurrentSchoolYearAction() {
  // Verify session

  try {
    const result = await WorkdaysAPI.initializeCurrentSchoolYear();
    revalidatePath("/workdays");

    return {
      success: true,
      status: result?.status ?? "unknown",
    };
  } catch (error) {
    let errorMessage = "An unexpected error occurred.";

    if (error.response >= 400 && error.response < 500) {
      errorMessage = "Invalid input data. Please check entry fields.";
    } else if (error.response >= 500) {
      errorMessage = "Server error. Please try again later.";
    }

    return {
      success: false,
      message: errorMessage,
    };
  }
}
