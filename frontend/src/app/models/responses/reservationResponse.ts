import { ReservationStatus } from "../requests/makeReservation";

export class FeedbackResponse {
  reservationId: number = 0;
  rating: number = 0;
  comment: string | null = null;
  dateTimeCreation: Date = new Date();
}

export class ReservationResponse {
  id: number = 0;
  touristFirstname: string = '';
  touristLastname: string = '';
  cottageName: string = '';
  cottageLocation: string = '';

  datetimeStart: Date = new Date();
  datetimeEnd: Date = new Date();
  adultsNumber: number = 0;
  childrenNumber: number = 0;
  cost: number = 0.0;
  specialDemands: string = '';
  status: ReservationStatus = ReservationStatus.PENDING;
  rejectionComment: string | null = null;

  feedback: FeedbackResponse | null = new FeedbackResponse();
  newRating:number = 0;
  newComment: string|null = null;
  newRateErrMsg: string = '';
}
