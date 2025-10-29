# Script para resolver conflictos de merge
# Ejecutar con: .\resolve-conflicts.ps1

Write-Host "Marcando archivos 'both added' como resueltos..." -ForegroundColor Yellow

git add backend/src/main/java/com/imagicode/agorasoftadmin/controladores/OrganizationController.java
git add backend/src/main/java/com/imagicode/agorasoftadmin/entidades/Organization.java
git add backend/src/main/java/com/imagicode/agorasoftadmin/entidades/Subscription.java
git add backend/src/main/java/com/imagicode/agorasoftadmin/repositorios/OrganizationRepository.java
git add backend/src/main/java/com/imagicode/agorasoftadmin/servicios/OrganizationService.java
git add frontend/src/app/services/organization.service.ts

Write-Host "Marcando archivos 'both modified' como resueltos..." -ForegroundColor Yellow

git add backend/src/main/java/com/imagicode/agorasoftadmin/servicios/UsuarioService.java
git add frontend/src/app/app.routes.ts
git add frontend/src/app/layouts/arquitectura-layout/arquitectura-layout.html
git add frontend/src/app/layouts/tesoreria-layout/tesoreria-layout.html
git add frontend/src/app/pages/tesoreria/tesoreria-dashboard/tesoreria-dashboard.html
git add frontend/src/app/pages/tesoreria/tesoreria-dashboard/tesoreria-dashboard.ts

Write-Host "`nVerificando estado..." -ForegroundColor Green
git status

Write-Host "`nConflictos resueltos. Puedes completar el merge con:" -ForegroundColor Green
Write-Host "  git commit -m 'Resolve merge conflicts and add missing obtenerUsuariosPorOrganizacion method'" -ForegroundColor Cyan
