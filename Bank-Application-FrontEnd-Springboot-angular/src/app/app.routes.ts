import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { MyTransactionsComponent } from './pages/my-transactions/my-transactions.component';
import { MainPageComponent } from './pages/main-page/main-page.component';
import { MyContactsComponent } from './pages/my-contacts/my-contacts.component';
import { NewTransactionComponent } from './pages/new-transaction/new-transaction.component';
import { NewContactComponent } from './pages/new-contact/new-contact.component';
import { UserComponent } from './pages/user-dashboard/user.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { MainAdminComponent } from './admin/main-admin/main-admin.component';
import { UsersManagerComponent } from './admin/users-manager/users-manager.component';
import { AdminDashboardComponent } from './admin/admin-dashboard/admin-dashboard.component';
import { NgModule } from '@angular/core';
import { ConfirmRegisterComponent } from './pages/confirm-register/confirm-register.component';
import { AdminGuardService } from './services/guard/admin-guard/admin-guard.service';
import { TokenGuardService } from './services/guard/token-guard/token-guard.service';
import { AccessDeniedComponent } from './pages/access-denied/access-denied.component';
import { EdocumentsComponent } from './pages/edocuments/edocuments.component';

export const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'confirm-register', component: ConfirmRegisterComponent },

  { path: 'access-denied', component: AccessDeniedComponent },
  { path: 'login', component: LoginComponent },
    { path: '', redirectTo:'/login',pathMatch: 'full'},
  {
    path: 'user',
    component: MainPageComponent,
    canActivate: [TokenGuardService],

    children: [
      { path: 'my-transaction', component: MyTransactionsComponent },
      { path: 'my-contact', component: MyContactsComponent },
      { path: 'new-transaction', component: NewTransactionComponent },
      { path: 'new-contact', component: NewContactComponent },
      { path: 'new-contact/:id', component: NewContactComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'dashboard', component: UserComponent },
      { path: 'document', component: EdocumentsComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },
  {
    path: 'admin',
    component: MainAdminComponent,
    canActivate: [TokenGuardService, AdminGuardService],
    children: [
      { path: 'clients', component: UsersManagerComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'dashboard', component: AdminDashboardComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
