import {Directive} from '@angular/core';
import {AbstractControl, AsyncValidator, NG_ASYNC_VALIDATORS, ValidationErrors} from "@angular/forms";
import {UserService} from "../../services/user/user.service";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Directive({
  selector: '[uniqueUsername]',
  providers: [{provide: NG_ASYNC_VALIDATORS, useExisting: UniqueUsernameValidation, multi: true}]
})
export class UniqueUsernameValidation implements AsyncValidator {

  constructor(private userService: UserService) {
  }

  registerOnValidatorChange(fn: () => void): void {
  }

  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null>;
  validate(control: AbstractControl): ValidationErrors | null;
  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> | ValidationErrors | null {
    console.log(control.value);
    return this.userService.checkUsernameAvailability(control.value).pipe(
      map(users => {
        return users && !users['available'] ? {'uniqueUsername': true} : null;
      })
    );
  }

}
