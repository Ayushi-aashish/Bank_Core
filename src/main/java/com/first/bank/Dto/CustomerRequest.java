package com.first.bank.Dto;

import jakarta.validation.constraints.*;
public class CustomerRequest {

        @NotBlank(message = "Customer Name is required")

        private String name;

        @NotBlank(message = "Customer Email is required")
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

