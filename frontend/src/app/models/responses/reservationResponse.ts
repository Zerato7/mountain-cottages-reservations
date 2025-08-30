export class FeedbackResponse {
  rating: number = 0;
  comment: string | null = null;
}

export enum Status {
  PENDING = 'PENDING',
  ACCEPTED_BY_OWNER = 'ACCEPTED_BY_OWNER',
  CANCELLED_BY_TOURIST = 'CANCELLED_BY_TOURIST',
  REJECTED_BY_OWNER = 'REJECTED_BY_OWNER'
}

export class ReservationResponse {
  username: string = '';
  cottageName: string = '';

  datetimeStart: Date = new Date();
  datetimeEnd: Date = new Date();
  adultsNumber: number = 0;
  childrenNumber: number = 0;
  specialDemands: string = '';
  status: Status = Status.PENDING;
  rejectionComment: string | null = null;

  feedback: FeedbackResponse | null = new FeedbackResponse();
}
