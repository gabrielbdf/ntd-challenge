# Angular Project: User Balance Operations Site

This README provides instructions on setting up and using an Angular project that includes a user login form, a toolbar displaying user balance, and a set of operations for user interaction.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js** (v14 or above) - [Download](https://nodejs.org/)
- **Angular CLI** (v12 or above) - Install with `npm install -g @angular/cli`
- **TypeScript** (v4 or above) - Install with `npm install -g typescript`

## Installation

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Run the application:**
   ```bash
   ng serve
   ```
   The application will be available at `http://localhost:4200/`.

## Project Structure

The project consists of the following main components:

### 1. **Login Component**

This component handles user authentication.

- **Template:** Displays a form with fields for username and password.
- **Validation:** Uses Angular Reactive Forms for form validation.
- **Action:** On form submission, it triggers the `makeLogin()` method.

**Key Code Snippet:**

```html
<form (submit)="makeLogin()" [formGroup]="formLogin">
    <!-- Username Field -->
    <mat-form-field appearance="fill">
        <mat-label>Username</mat-label>
        <input matInput placeholder="Username" formControlName="username">
        <mat-icon matSuffix>person</mat-icon>
    </mat-form-field>
    
    <!-- Password Field -->
    <mat-form-field appearance="outline">
        <mat-label>Password</mat-label>
        <input matInput type="password" placeholder="Password" formControlName="password">
        <mat-icon matSuffix>lock</mat-icon>
    </mat-form-field>
    
    <!-- Error Display -->
    <mat-error *ngIf="formLogin.errors">{{formLogin.errors | json}}</mat-error>
    
    <!-- Submit Button -->
    <button type="submit" mat-flat-button>Login</button>
</form>
```

### 2. **Toolbar Component**

Displays the user's current balance retrieved from the `authService`.

**Key Code Snippet:**

```html
<mat-toolbar>
    <button mat-icon-button aria-label="Example icon-button with menu icon">
        <mat-icon>menu</mat-icon>
    </button>
    <span><strong>User Balance:</strong> {{ (authService.userDetails | async)?.balance }}</span>
</mat-toolbar>
```

### 3. **Operations Component**

Allows the user to perform a series of predefined operations.

- **Operations List:** Dynamically generated tabs based on available operations.
- **Form:** Each operation has an associated form to input arguments and view the result.
- **Submission:** On form submission, the result is computed and displayed.

**Key Code Snippet:**

```html
<mat-tab-group mat-stretch-tabs="false" mat-align-tabs="start">
    <mat-tab *ngFor="let operation of operations; let j = index" [label]="operation[0]">
        <mat-card appearance="outlined">
            <mat-card-content>
                <form [formGroup]="operationsForms[j]" (submit)="submit(operationsForms[j])">
                    <mat-form-field>
                        <label>Operation</label>
                        <input matInput type="text" formControlName="operation">
                    </mat-form-field>
                    
                    <div formArrayName="args">
                        <mat-form-field *ngFor="let arg of getControls(operationsForms[j].get('args')); let i = index">
                            <input type="number" [placeholder]="'Number ' + (i + 1)" matInput [formControlName]="i">
                        </mat-form-field>
                    </div>
                    
                    <mat-divider></mat-divider>
                    
                    <mat-form-field>
                        <label>Result</label>
                        <input readonly="readonly" matInput formControlName="result">
                    </mat-form-field>
                    
                    <button type="submit" mat-raised-button color="primary">Make Operation</button>
                </form>
            </mat-card-content>
        </mat-card>
    </mat-tab>
</mat-tab-group>
```

## Running Tests

To run the tests for this project:

```bash
ng test
```

## Build

To build the project for production:

```bash
ng build --prod
```

The build artifacts will be stored in the `dist/` directory.

## Further Reading

- [Angular Documentation](https://angular.io/docs)
- [Angular Material](https://material.angular.io/)


