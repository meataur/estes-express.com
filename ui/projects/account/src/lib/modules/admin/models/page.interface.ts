import { Profile } from './profile.interface';

export interface Page {
  content: Array<Profile>;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  totalElements: number;
  totalPages: number;
}
