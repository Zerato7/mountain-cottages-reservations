import { ReservationStatus } from "./makeReservation";

export class ReservationStatusUpdate {
  id: number = 0;
  status: ReservationStatus = ReservationStatus.PENDING;
  rejectionComment: string | null = null;
}
