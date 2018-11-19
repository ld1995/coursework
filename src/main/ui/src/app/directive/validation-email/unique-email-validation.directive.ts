import {Directive} from '@angular/core';
import {AbstractControl, AsyncValidator, NG_ASYNC_VALIDATORS, ValidationErrors} from "@angular/forms";
import {Observable} from "rxjs";
import {UserService} from "../../services/user/user.service";
import {map} from "rxjs/operators";

@Directive({
  selector: '[uniqueEmail]',
  providers: [{provide: NG_ASYNC_VALIDATORS, useExisting: UniqueEmailValidation, multi: true}]
})
export class UniqueEmailValidation implements AsyncValidator {

  constructor(private userService: UserService) {
  }

  registerOnValidatorChange(fn: () => void): void {
  }

  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null>;
  validate(control: AbstractControl): ValidationErrors | null;
  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> | ValidationErrors | null {
    return this.userService.checkEmailAvailability(control.value).pipe(
      map(users => {
        return users && !users['available'] ? {'uniqueEmail': true} : null;
      })
    );
  }

}
