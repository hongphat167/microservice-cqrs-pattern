package com.hongphat.common_service.constant;

import lombok.Getter;

/**
 * EmailTemplateConstants
 *
 * @author hongp
 * @description Happy Coding With Phat 😊😊
 * @since 11:46 SA 12/01/2025
 */
@Getter
public class EmailTemplateConstants {
	public static final String EMAIL_TEMPLATE = """
			<!DOCTYPE html>
			<html>
			<head>
			    <meta charset="utf-8">
			    <meta name="viewport" content="width=device-width, initial-scale=1.0">
			    <title>Notification Email</title>
			    <style>
			        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&family=Inter:wght@400;500;600&display=swap');
			
			        body {
			            font-family: 'Inter', sans-serif;
			            line-height: 1.6;
			            margin: 0;
			            padding: 0;
			            background-color: #f8fafc;
			            color: #334155;
			        }
			
			        .container {
			            max-width: 600px;
			            margin: 20px auto;
			            background: #ffffff;
			            border-radius: 16px;
			            overflow: hidden;
			            box-shadow: 0 8px 30px rgba(0,0,0,0.08);
			            animation: fadeIn 0.8s ease-out;
			        }
			
			        @keyframes fadeIn {
			            from {
			                opacity: 0;
			                transform: translateY(20px);
			            }
			            to {
			                opacity: 1;
			                transform: translateY(0);
			            }
			        }
			
			        .header {
			            background: linear-gradient(135deg, #4f46e5 0%, #6366f1 100%);
			            color: #ffffff;
			            padding: 40px 20px;
			            text-align: center;
			            position: relative;
			            overflow: hidden;
			        }
			
			        .header::before {
			            content: '';
			            position: absolute;
			            top: 0;
			            left: 0;
			            right: 0;
			            bottom: 0;
			            background: radial-gradient(circle at 70% 30%, rgba(255,255,255,0.2) 0%, transparent 70%);
			            animation: shimmer 4s infinite;
			        }
			
			        @keyframes shimmer {
			            0% { transform: scale(1); opacity: 0.5; }
			            50% { transform: scale(1.2); opacity: 0.3; }
			            100% { transform: scale(1); opacity: 0.5; }
			        }
			
			        .logo {
			            width: 90px;
			            height: 90px;
			            background: #ffffff;
			            border-radius: 50%;
			            margin: 0 auto 20px;
			            display: flex;
			            align-items: center;
			            justify-content: center;
			            position: relative;
			            box-shadow: 0 4px 20px rgba(0,0,0,0.15);
			            border: 4px solid rgba(255,255,255,0.2);
			            animation: pulse 2s infinite;
			        }
			
			        @keyframes pulse {
			            0% { transform: scale(1); }
			            50% { transform: scale(1.05); }
			            100% { transform: scale(1); }
			        }
			
			        .logo img {
			            width: 100%;
			            height: 100%;
			            border-radius: 50%;
			            object-fit: cover;
			            transition: transform 0.3s ease;
			        }
			
			        .logo img:hover {
			            transform: scale(1.1);
			        }
			
			        .company-name {
			            font-family: 'Poppins', sans-serif;
			            font-size: 26px;
			            font-weight: 600;
			            margin: 0;
			            letter-spacing: 0.5px;
			            text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
			        }
			
			        .content {
			            padding: 40px;
			            background: #ffffff;
			            animation: slideUp 0.6s ease-out 0.3s both;
			        }
			
			        @keyframes slideUp {
			            from {
			                opacity: 0;
			                transform: translateY(20px);
			            }
			            to {
			                opacity: 1;
			                transform: translateY(0);
			            }
			        }
			
			        .greeting {
			            font-family: 'Poppins', sans-serif;
			            font-size: 22px;
			            font-weight: 600;
			            margin-bottom: 25px;
			            color: #1e293b;
			        }
			
			        .message {
			            color: #475569;
			            margin-bottom: 30px;
			            font-size: 16px;
			            line-height: 1.8;
			        }
			
			        .message ul {
			            background: #f8fafc;
			            border-radius: 12px;
			            padding: 25px;
			            margin: 20px 0;
			            border-left: 4px solid #4f46e5;
			            box-shadow: 0 4px 15px rgba(0,0,0,0.03);
			        }
			
			        .message li {
			            margin: 12px 0;
			            list-style-type: none;
			            position: relative;
			            padding-left: 28px;
			            transition: transform 0.2s ease;
			        }
			
			        .message li:hover {
			            transform: translateX(5px);
			        }
			
			        .message li:before {
			            content: "→";
			            position: absolute;
			            left: 0;
			            color: #4f46e5;
			            font-weight: bold;
			        }
			
			        .cta-button {
			            display: inline-block;
			            padding: 14px 35px;
			            background: linear-gradient(135deg, #4f46e5 0%, #6366f1 100%);
			            color: #ffffff;
			            text-decoration: none;
			            border-radius: 30px;
			            font-weight: 500;
			            font-size: 16px;
			            border: none;
			            cursor: pointer;
			            transition: all 0.3s ease;
			            box-shadow: 0 4px 15px rgba(79, 70, 229, 0.3);
			            position: relative;
			            overflow: hidden;
			        }
			
			        .cta-button:hover {
			            transform: translateY(-2px);
			            box-shadow: 0 6px 20px rgba(79, 70, 229, 0.4);
			        }
			
			        .cta-button::after {
			            content: '';
			            position: absolute;
			            top: -50%;
			            left: -50%;
			            width: 200%;
			            height: 200%;
			            background: rgba(255,255,255,0.2);
			            transform: rotate(45deg);
			            transition: all 0.5s ease;
			        }
			
			        .cta-button:hover::after {
			            transform: rotate(45deg) translateX(80%);
			        }
			
			        .footer {
			            padding: 30px;
			            text-align: center;
			            background: #f8fafc;
			            border-top: 1px solid rgba(79, 70, 229, 0.1);
			            animation: fadeIn 0.6s ease-out 0.6s both;
			        }
			
			        .social-links {
			            margin: 20px 0;
			        }
			
			        .social-links a {
			            display: inline-block;
			            color: #4f46e5;
			            text-decoration: none;
			            margin: 0 15px;
			            font-weight: 500;
			            position: relative;
			            transition: all 0.3s ease;
			        }
			
			        .social-links a::after {
			            content: '';
			            position: absolute;
			            width: 0;
			            height: 2px;
			            background: #4f46e5;
			            bottom: -4px;
			            left: 0;
			            transition: width 0.3s ease;
			        }
			
			        .social-links a:hover {
			            color: #6366f1;
			        }
			
			        .social-links a:hover::after {
			            width: 100%;
			        }
			
			        .copyright {
			            font-size: 14px;
			            color: #64748b;
			            margin: 20px 0;
			        }
			
			        .disclaimer {
			            font-size: 12px;
			            color: #94a3b8;
			            margin-top: 15px;
			            line-height: 1.6;
			            max-width: 80%;
			            margin-left: auto;
			            margin-right: auto;
			        }
			
			        @media only screen and (max-width: 600px) {
			            .container {
			                margin: 0;
			                border-radius: 0;
			            }
			
			            .content {
			                padding: 25px;
			            }
			
			            .message ul {
			                padding: 20px;
			            }
			        }
			    </style>
			</head>
			<body>
			    <div class="container">
			        <div class="header">
			            <div class="logo">
			                <img src="https://media.istockphoto.com/id/1201144331/vi/vec-to/logo-y%E1%BA%BFu-t%E1%BB%91-thi%E1%BA%BFt-k%E1%BA%BF-bi%E1%BB%83u-t%C6%B0%E1%BB%A3ng-cho-c%C3%B4ng-ty-%C4%91%E1%BB%95i-m%E1%BB%9Bi-c%C3%B4ng-ngh%E1%BB%87-bi%E1%BB%83u-t%C6%B0%E1%BB%A3ng-v%C3%A0-bi%E1%BB%83u-t%C6%B0%E1%BB%A3ng-c%C3%B4ng.jpg?s=1024x1024&w=is&k=20&c=5dmRWRCRGSFwapr1Xz6AjDEgHnkev6-OwaCA3wr4urw=" alt="Company Logo">
			            </div>
			            <h1 class="company-name">Công ty TNHH Hồng Phát</h1>
			        </div>
			
			        <div class="content">
			            <div class="greeting">
			                Xin chào %recipient_name%,
			            </div>
			
			            <div class="message">
			                <p>Chúng tôi rất vui mừng được thông báo đến bạn về %notification_content%.</p>
			
			                <p>Đây là một thông báo quan trọng từ hệ thống của chúng tôi. Vui lòng kiểm tra chi tiết bên dưới:</p>
			
			                <ul>
			                    <li>Thời gian: %timestamp%</li>
			                    <li>Mã tham chiếu: %reference_code%</li>
			                    <li>Trạng thái: %status%</li>
			                </ul>
			
			                <p>Để biết thêm chi tiết, vui lòng click vào nút bên dưới:</p>
			            </div>
			
			            <center>
			                <a href="%action_url%" class="cta-button">Xem Chi Tiết</a>
			            </center>
			        </div>
			
			        <div class="footer">
			            <div class="social-links">
			                <a href="#">Facebook</a>
			                <a href="#">Twitter</a>
			                <a href="#">LinkedIn</a>
			            </div>
			
			            <div class="copyright">
			                © 2025 Công ty TNHH Hồng Phát. Tất cả quyền được bảo lưu.
			            </div>
			
			            <div class="disclaimer">
			                Email này được gửi tự động, vui lòng không trả lời. Nếu bạn cần hỗ trợ,
			                vui lòng liên hệ với chúng tôi qua email hogphat1607@gmail.com
			            </div>
			        </div>
			    </div>
			</body>
			</html>
			""";
}
