import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
import {environment} from '../environments/environment';

@Injectable()
export class KeycloakService {

  constructor(private http: HttpClient) {}

  userRegistration(user: User) {
    let url = environment.url + `/engine-rest/process-definition/key/registration_user_process/start`;

    const options = {
        variables: {
          params: {
            value: JSON.stringify(user),
            type: 'String',
          }
        },
        businessKey : 'myBusinessKey'
    };

    const headers =  new HttpHeaders({
      'Content-Type': 'application/json; charset=UTF-8'
    });

    return this.http.post<any>(url, options, {headers});
  }
}
