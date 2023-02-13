export const authRoles = {
  sa: ['SA'], // Only Super Admin has access
  admin: ['SA', 'ADMIN'], // Only SA & Admin has access
  leader: ['SA', 'ADMIN', 'LEADER'], // Only SA & Admin & Editor has access
  hr: ['SA', 'ADMIN', 'LEADER', 'HR'], // Everyone has access
  accountancy: ['SA', 'ADMIN', 'LEADER', 'ACCOUNTANCY'], // Everyone has access
  guest: ['SA', 'ADMIN', 'LEADER', 'HR', 'ACCOUNTANCY'], // Everyone has access
};
