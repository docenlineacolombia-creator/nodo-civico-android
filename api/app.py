from flask import Flask, request, jsonify
from datetime import datetime
import uuid

app = Flask(__name__)

# -------------------------------------------------------
# Datos en memoria (reemplazar por SQLite/PostgreSQL real)
# -------------------------------------------------------
categories = [
    {"id": "cat1", "name": "Alumbrado",          "iconEmoji": "💡"},
    {"id": "cat2", "name": "Aseo",               "iconEmoji": "🗑️"},
    {"id": "cat3", "name": "Seguridad",          "iconEmoji": "🔒"},
    {"id": "cat4", "name": "Servicios públicos", "iconEmoji": "💧"},
]

reports = [
    {
        "id":          "r1",
        "remoteId":    "r1",
        "userId":      1,
        "title":       "Luminaria dañada en la calle 12",
        "description": "La lámpara lleva 3 noches apagada.",
        "category":    "Alumbrado",
        "priority":    "ALTA",
        "status":      "OPEN",
        "location":    "Calle 12 con carrera 5",
        "createdAt":   1715000000000,
        "updatedAt":   1715000000000,
        "pendingSync": False,
    },
    {
        "id":          "r2",
        "remoteId":    "r2",
        "userId":      1,
        "title":       "Basura acumulada en el parque central",
        "description": "Bolsas sin recoger desde hace 2 días.",
        "category":    "Aseo",
        "priority":    "MEDIA",
        "status":      "IN_PROGRESS",
        "location":    "Parque central bloque A",
        "createdAt":   1715100000000,
        "updatedAt":   1715100000000,
        "pendingSync": False,
    },
]


# -------------------------------------------------------
# Helpers
# -------------------------------------------------------
def now_ms():
    return int(datetime.utcnow().timestamp() * 1000)


# -------------------------------------------------------
# Categorias
# -------------------------------------------------------
@app.route("/categories", methods=["GET"])
def get_categories():
    return jsonify(categories)


# -------------------------------------------------------
# Reportes
# -------------------------------------------------------
@app.route("/reports", methods=["GET"])
def get_reports():
    return jsonify(reports)


@app.route("/reports/<report_id>", methods=["GET"])
def get_report(report_id):
    report = next((r for r in reports if r["id"] == report_id), None)
    if not report:
        return jsonify({"error": "Not found"}), 404
    return jsonify(report)


@app.route("/reports", methods=["POST"])
def create_report():
    data = request.get_json(force=True)
    if not data:
        return jsonify({"error": "Body requerido"}), 400

    required = ["title", "description", "category", "priority"]
    for field in required:
        if not data.get(field):
            return jsonify({"error": f"Campo obligatorio: {field}"}), 422

    new_id = str(uuid.uuid4())[:8]
    report = {
        "id":          new_id,
        "remoteId":    new_id,
        "userId":      data.get("userId", 1),
        "title":       data["title"],
        "description": data["description"],
        "category":    data["category"],
        "priority":    data["priority"],
        "status":      data.get("status", "OPEN"),
        "location":    data.get("location", ""),
        "createdAt":   now_ms(),
        "updatedAt":   now_ms(),
        "pendingSync": False,
    }
    reports.append(report)
    return jsonify(report), 201


@app.route("/reports/<report_id>", methods=["PUT"])
def update_report(report_id):
    report = next((r for r in reports if r["id"] == report_id), None)
    if not report:
        return jsonify({"error": "Not found"}), 404

    data = request.get_json(force=True) or {}
    for field in ["title", "description", "category", "priority", "status", "location"]:
        if field in data:
            report[field] = data[field]
    report["updatedAt"] = now_ms()
    report["pendingSync"] = False
    return jsonify(report)


@app.route("/reports/<report_id>", methods=["DELETE"])
def delete_report(report_id):
    global reports
    reports = [r for r in reports if r["id"] != report_id]
    return jsonify({"deleted": report_id})


# -------------------------------------------------------
# Usuario simple (identificación sin auth compleja)
# -------------------------------------------------------
users = [{"id": 1, "name": "Usuario Nodo", "email": "usuario@nodocivico.co", "sector": "Barrio Centro"}]

@app.route("/users", methods=["POST"])
def identify_user():
    data = request.get_json(force=True) or {}
    existing = next((u for u in users if u["email"] == data.get("email")), None)
    if existing:
        return jsonify(existing)
    new_user = {
        "id":     len(users) + 1,
        "name":   data.get("name", "Nuevo usuario"),
        "email":  data.get("email", ""),
        "sector": data.get("sector", ""),
    }
    users.append(new_user)
    return jsonify(new_user), 201


@app.route("/users/<int:user_id>", methods=["GET"])
def get_user(user_id):
    user = next((u for u in users if u["id"] == user_id), None)
    if not user:
        return jsonify({"error": "Not found"}), 404
    return jsonify(user)


# -------------------------------------------------------
# Health check
# -------------------------------------------------------
@app.route("/health", methods=["GET"])
def health():
    return jsonify({"status": "ok", "reports": len(reports), "categories": len(categories)})


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
