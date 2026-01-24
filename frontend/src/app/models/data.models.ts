export interface Terrain {
    id?: number;
    nom: string;
    description: string;
    localisation: string;
    etat: string;
    prix: number;
}

export interface Creneau {
    id?: number;
    date: string;
    heureDebut: string;
    heureFin: string;
    terrain: Terrain;
    reserve?: boolean;
}

export interface Reservation {
    id?: number;
    dateReservation?: string;
    statut: string;
    utilisateur?: any;
    terrain: Terrain;
    creneau: Creneau;
}
