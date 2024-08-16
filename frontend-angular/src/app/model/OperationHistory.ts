export interface OperationHistory {
    id: number;
    operation: string;
    amount: number;
    response: string;
    timestamp: Date;
    userBalance: number;
}