#!/bin/bash
# Script quản lý Docker cho Git Bash trên Windows

# Màu sắc cho output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

# Hàm logging
log() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1"
    exit 1
}

info() {
    echo -e "${BLUE}[DEBUG]${NC} $1"
}

# Kiểm tra dependencies
check_dependencies() {
    log "Kiểm tra dependencies..."

    # Kiểm tra Docker
    if ! command -v docker &> /dev/null; then
        error "Docker chưa được cài đặt. Vui lòng cài đặt Docker Desktop."
    fi

    # Kiểm tra Docker Compose (hỗ trợ cả phiên bản cũ và mới)
    if ! command -v docker-compose &> /dev/null && ! docker compose version &> /dev/null; then
        error "Docker Compose chưa được cài đặt. Vui lòng cài đặt Docker Desktop."
    fi

    info "✓ Đã cài đặt đầy đủ dependencies"
}

# Dọn dẹp Docker resources
cleanup() {
    log "Đang dọn dẹp Docker resources..."

    # Dừng và xóa containers
    # Thử cả 2 lệnh để tương thích với các phiên bản Docker khác nhau
    docker-compose down --remove-orphans 2>/dev/null || \
    docker compose down --remove-orphans 2>/dev/null

    # Xóa unused images, networks và volumes
    docker system prune -af --volumes

    info "✓ Đã dọn dẹp xong Docker resources"
}

# Build service
build_service() {
    local service=$1

    if [ -z "$service" ]; then
        log "Build tất cả services..."
        docker-compose build --no-cache || docker compose build --no-cache
    else
        log "Build service: $service..."
        docker-compose build --no-cache "$service" || docker compose build --no-cache "$service"
    fi

    if [ $? -eq 0 ]; then
        info "✓ Build thành công"
    else
        error "Build thất bại"
    fi
}

# Chạy service
run_service() {
    local service=$1

    if [ -z "$service" ]; then
        log "Khởi động tất cả services..."
        docker-compose up -d || docker compose up -d
    else
        log "Khởi động service: $service..."
        docker-compose up -d "$service" || docker compose up -d "$service"
    fi

    if [ $? -eq 0 ]; then
        info "✓ Khởi động thành công"
    else
        error "Khởi động thất bại"
    fi
}

# Show logs
show_logs() {
    local service=$1

    if [ -z "$service" ]; then
        docker-compose logs -f || docker compose logs -f
    else
        docker-compose logs -f "$service" || docker compose logs -f "$service"
    fi
}

# Main script
main() {
    # Kiểm tra số lượng tham số
    if [ $# -eq 0 ]; then
        error "Vui lòng cung cấp tham số. Sử dụng ./build.sh help để xem hướng dẫn."
    fi

    # Gọi hàm kiểm tra dependencies trước khi thực hiện bất kỳ thao tác nào
    check_dependencies

    case "$1" in
        "clean")
            cleanup
            ;;
        "build")
            build_service "$2"
            ;;
        "run")
            run_service "$2"
            ;;
        "rebuild")
            if [ -z "$2" ]; then
                log "Rebuild và khởi động tất cả services..."
                cleanup
                docker-compose up -d --build || docker compose up -d --build
            else
                log "Rebuild và khởi động service: $2..."
                docker-compose up -d --build "$2" || docker compose up -d --build "$2"
            fi
            ;;
        "logs")
            show_logs "$2"
            ;;
        "help")
            echo -e "${BLUE}Usage:${NC}"
            echo "  ./build.sh clean              - Dọn dẹp Docker resources"
            echo "  ./build.sh build [service]    - Build một hoặc tất cả services"
            echo "  ./build.sh run [service]      - Chạy một hoặc tất cả services"
            echo "  ./build.sh rebuild [service]  - Rebuild và chạy lại services"
            echo "  ./build.sh logs [service]     - Xem logs của services"
            echo
            echo -e "${BLUE}Examples:${NC}"
            echo "  ./build.sh build book-service"
            echo "  ./build.sh run"
            echo "  ./build.sh rebuild"
            ;;
        *)
            error "Tham số không hợp lệ. Sử dụng ./build.sh help để xem hướng dẫn."
            ;;
    esac
}

main "$@"