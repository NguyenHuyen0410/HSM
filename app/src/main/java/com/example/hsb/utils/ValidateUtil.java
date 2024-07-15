package com.example.hsb.utils;

import android.widget.EditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidateUtil {
    public static boolean isEmailValid(EditText etEmail) {
        String email = etEmail.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email address");
            etEmail.requestFocus();
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }

    public static boolean isPasswordValid(EditText etPassword) {
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        } else if (password.length() < 8) {
            etPassword.setError("Password must be at least 8 characters");
            etPassword.requestFocus();
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

    public static boolean isPassEqual(EditText etPassword, EditText etConfirmPassword) {
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password doesn't match");
            etConfirmPassword.requestFocus();
            return false;
        } else {
            etConfirmPassword.setError(null);
            return true;
        }
    }

    public static boolean isNameValid(EditText etName) {
        String name = etName.getText().toString();
        if (name.isEmpty() || name.length() < 6) {
            etName.setError("Name is required, at least 5 characters");
            etName.requestFocus();
            return false;
        } else {
            etName.setError(null);
            return true;
        }
    }

    public static boolean isNameValid(String name) {
        return !name.isEmpty() && name.length() >= 6;
    }

    public static boolean isPhoneValid(EditText etPhone) {
        String phone = etPhone.getText().toString();
        if (phone.isEmpty()) {
            etPhone.setError("Phone number is required");
            etPhone.requestFocus();
            return false;
        } else if (!phone.matches("^(0|\\+84|0084|84)\\d{9}$")) {
            etPhone.setError("Please enter a valid phone number");
            etPhone.requestFocus();
            return false;
        } else {
            etPhone.setError(null);
            return true;
        }
    }

    public static boolean isAddressValid(EditText etAddress) {
        String address = etAddress.getText().toString();
        if (address.isEmpty() || address.length() < 10) {
            etAddress.setError("Address is required and must be at least 10 characters");
            etAddress.requestFocus();
            return false;
        } else {
            etAddress.setError(null);
            return true;
        }
    }

    public static boolean isDateValid(EditText etDateOfBirth) {
        String dateOfBirth = etDateOfBirth.getText().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        if (dateOfBirth.isEmpty()) {
            etDateOfBirth.setError("Date of birth is required");
            etDateOfBirth.requestFocus();
            return false;
        }
        try {
            // Parse the date string to LocalDateTime
            LocalDateTime.parse(dateOfBirth, formatter);
            etDateOfBirth.setError(null);
            return true;
        } catch (DateTimeParseException e) {
            // Handle the case where parsing fails
            etDateOfBirth.setError("Invalid date format. Please use dd/MM/yyyy HH:mm:ss");
            etDateOfBirth.requestFocus();
            return false;
        }
    }

}
