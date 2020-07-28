import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { User } from '../user';
import {KeycloakService} from '../keycloak.service';

@Component({
  selector: 'app-user-registration-page',
  templateUrl: './user-registration-page.component.html',
  styleUrls: ['./user-registration-page.component.scss']
})
export class UserRegistrationPageComponent implements OnInit {
  userForm: FormGroup;
  user: User = new User();


  // Объект с ошибками, которые будут выведены в пользовательском интерфейсе
  formErrors = {
    'login': '',
    'firstName': '',
    'lastName': '',
    'email': '',
    'password': ''
  };

  // Объект с сообщениями ошибок
  validationMessages = {
    'login' : {
      'required': 'Обязательное поле.',
      'minlength': 'Значение должно быть не менее 4х символов.'
    },
    'firstName': {
      'required': 'Обязательное поле.',
      'minlength': 'Значение должно быть не менее 4х символов.'
    },
    'lastName': {
      'required': 'Обязательное поле.',
      'minlength': 'Значение должно быть не менее 4х символов.'
    },
    'email': {
      'required': 'Обязательное поле.',
      'pattern': 'Не правильный формат email адреса.'
    },
    'password': {
      'required': 'Обязательное поле.',
      'minlength': 'Значение должно быть не менее 8ми символов.'
    }
  };

  constructor(private fb: FormBuilder, private ks: KeycloakService) { }

  ngOnInit() {
    this.buildForm();
  }

  buildForm() {
    this.userForm = this.fb.group({
      'login': [this.user.login, [
        Validators.required,
        Validators.minLength(4)
      ]],
      'firstName': [this.user.firstName, [
        Validators.required,
        Validators.minLength(4)
      ]],
      'lastName': [this.user.lastName, [
        Validators.required,
        Validators.minLength(4)
      ]],
      'email': [this.user.email, [
        Validators.required,
        Validators.pattern('[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}')
      ]],
      'password': [this.user.password, [
        Validators.required,
      ]]
    });

    this.userForm.valueChanges
      .subscribe(data => this.onValueChange(data));

    this.onValueChange();
  }

  onValueChange(data?: any) {
    if (!this.userForm) return;
    let form = this.userForm;

    for (let field in this.formErrors) {
      this.formErrors[field] = '';
      // form.get - получение элемента управления
      let control = form.get(field);

      if (control && control.dirty && !control.valid) {
        let message = this.validationMessages[field];
        for (let key in control.errors) {
          this.formErrors[field] += message[key] + ' ';
        }
      }
    }
  }

  onSubmit() {
    console.log('submitted');
    console.log(this.userForm.value);
    this.ks.userRegistration(this.userForm.value).subscribe(data => console.log(data), error => console.log(error));
  }

}
